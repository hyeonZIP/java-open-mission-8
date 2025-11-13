// 페이지 로드 시 세션 에러 체크
document.addEventListener('DOMContentLoaded', function() {
    // URL에서 sessionError 파라미터 추출
    const urlParams = new URLSearchParams(window.location.search);
    const sessionError = urlParams.get('sessionError');

    if (sessionError) {
        let message = '';

        if (sessionError === 'expired') {
            message = '⚠️ 다른 기기에서 로그인하여 세션이 만료되었습니다.';
        } else if (sessionError === 'invalid') {
            message = '⚠️ 세션이 유효하지 않습니다. 다시 로그인해주세요.';
        }

        // Toastify가 로드되었는지 확인
        if (message && typeof Toastify !== 'undefined') {
            Toastify({
                text: message,
                duration: 3000,
                gravity: "top",
                position: "right",
                backgroundColor: "#ff4444",
            }).showToast();

            // Toast 표시 후 URL에서 파라미터 제거 (깔끔하게)
            const url = new URL(window.location);
            url.searchParams.delete('sessionError');
            window.history.replaceState({}, '', url);
        }
    }
});