package yoon.shop.test1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yoon.shop.test1.domain.Members;

@Repository
public interface MemberRepository extends JpaRepository<Members, Long> {
    Members findMembersByIdx(Long idx);

    Members findMembersByEmail(String email);
}
