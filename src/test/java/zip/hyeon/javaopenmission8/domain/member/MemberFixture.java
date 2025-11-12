package zip.hyeon.javaopenmission8.domain.member;

public class MemberFixture {
    public static MemberRegisterRequest createMemberRegisterRequest() {
        return new MemberRegisterRequest("githubId", "username", "email@naver.com");
    }

    public static MemberRegisterRequest updateMemberRegisterRequest() {
        return new MemberRegisterRequest("githubId", "username123", "email@gmail.com");
    }
}
