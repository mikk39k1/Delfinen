package utility.member_comparators;

import actors.Member;

public class SortByMemberName extends MemberComparator{
    @Override
    public int compare(Member member, Member otherMember) {
        return member.getName().compareTo(otherMember.getName());
    }
}
