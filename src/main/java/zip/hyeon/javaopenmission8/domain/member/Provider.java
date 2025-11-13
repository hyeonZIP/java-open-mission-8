package zip.hyeon.javaopenmission8.domain.member;

import lombok.Getter;

@Getter
public enum Provider {
    GITHUB("github");

    private final String value;

    Provider(String value) {
        this.value = value;
    }
}
