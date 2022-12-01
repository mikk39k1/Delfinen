package utility.member_comparators;

import actors.Member;

public class SortByMemberPhoneNumber extends MemberComparator{
    @Override
    public int compare(Member member, Member otherMember) {
        return member.getPhoneNumber().compareTo(otherMember.getPhoneNumber());
    }
}
