package yoon.shop.test1.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import yoon.shop.test1.domain.Members;
import yoon.shop.test1.dto.MemberDto;
import yoon.shop.test1.message.Role;
import yoon.shop.test1.repository.MemberRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberDetailService memberDetailService;
    private final BCryptPasswordEncoder passwordEncoder;

    public List<Members> read(){
        List<Members> list = memberRepository.findAll();
        return list;
    }

    public MemberDto read(String id)throws UsernameNotFoundException{
        Long idx = Long.parseLong(id);
        Members member = memberRepository.findMembersByIdx(idx);

        if(member == null){
            return null;
        }

        return new MemberDto(member.getEmail(), null, member.getName());
    }

    @Transactional
    public MemberDto join(MemberDto dto){

        if(dto.getEmail()==null || dto.getPassword()==null || dto.getName()==null)
            return null;

        Members member = Members.builder()
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .name(dto.getName())
                .role(Role.ROLE_USER)
                .build();
        memberRepository.save(member);

        return new MemberDto(member.getEmail(), null, member.getName());
    }

    public MemberDto login(MemberDto dto){

        Members member = memberRepository.findMembersByEmail(dto.getEmail());

        if(member == null)
            return new MemberDto("EE", null, null);    //Email Error
        if(!passwordEncoder.matches(dto.getPassword(),member.getPassword()))
            return new MemberDto("PE", null, null);    //Password Error

        return new MemberDto(member.getEmail(), null, member.getName());
    }

    @Transactional
    public MemberDto update(String id, MemberDto dto){
        Long idx = Long.parseLong(id);
        Members member = memberRepository.findMembersByIdx(idx);

        if(member == null)
            return new MemberDto("IE",null,null);    //Index Error
        if(!passwordEncoder.matches(dto.getPassword(), member.getPassword()))
            return new MemberDto("PE", null, null); //Password Error

        member.setEmail(dto.getEmail());
        member.setName(dto.getName());

        MemberDto dtoTemp = new MemberDto(member.getEmail(), null, member.getName());

        return dtoTemp;
    }

    @Transactional
    public MemberDto delete(String id, String password){
        Long idx = Long.parseLong(id);
        Members member = memberRepository.findMembersByIdx(idx);

        if(member == null)
            return new MemberDto("IE", null, null); //Index Error
        if(!passwordEncoder.matches(password, member.getPassword()))
            return new MemberDto("PE", null, null); //Password Error

        MemberDto dto = new MemberDto(member.getEmail(), null, member.getName());

        memberRepository.delete(member);

        return dto;
    }
}
