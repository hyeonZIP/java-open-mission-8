package zip.hyeon.javaopenmission8.domain.member;

public class MemberFixture {
    public static MemberRegisterRequest createMemberRegisterRequest() {
        return new MemberRegisterRequest(Provider.GITHUB, "12345", "username","https://profile/1.com");
    }

    public static MemberRegisterRequest updateMemberRegisterRequest() {
        return new MemberRegisterRequest(Provider.GITHUB, "12345", "username123","https://profile/2.com");
    }
}
