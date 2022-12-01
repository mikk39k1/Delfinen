package utility.member_comparators;

import actors.Member;

public class SortByMemberID extends MemberComparator {

    @Override
    public int compare(Member member, Member otherMember) {
        return Integer.compare(member.getUniqueID(), otherMember.getUniqueID());
    }
}
