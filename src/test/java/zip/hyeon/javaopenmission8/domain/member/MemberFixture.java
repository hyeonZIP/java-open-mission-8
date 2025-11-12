package zip.hyeon.javaopenmission8.domain.member;

public class MemberFixture {
    public static MemberRegisterRequest createMemberRegisterRequest() {
        return createMemberRegisterRequest("username", "https://profile/1.com");
    }

    public static MemberRegisterRequest createMemberRegisterRequest(String username, String profileImageUrl) {
        return new MemberRegisterRequest(Provider.GITHUB, "12345", username, profileImageUrl);
    }
}
