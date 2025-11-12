package zip.hyeon.javaopenmission8.domain.member;

public class MemberFixture {
    public static MemberRegisterRequest createMemberRegisterRequest() {
        return new MemberRegisterRequest(Provider.GITHUB, "12345", "username");
    }

    public static MemberRegisterRequest updateMemberRegisterRequest() {
        return new MemberRegisterRequest(Provider.GITHUB, "12345", "username123");
    }
}
