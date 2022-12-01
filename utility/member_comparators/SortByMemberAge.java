package utility.member_comparators;

import actors.Member;

public class SortByMemberAge extends MemberComparator {
    @Override
    public int compare(Member member, Member otherMember) {
        return Integer.compare(member.getAge(), otherMember.getAge());
    }
}
