package yoon.shop.test1.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import yoon.shop.test1.domain.Members;
import yoon.shop.test1.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class MemberDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Members member = memberRepository.findMembersByEmail(username);

        if(member == null)
            throw new UsernameNotFoundException(username);

        return member;
    }
}
