package com.foongdoll.portfolio.backend.core.util.mail.template;

public final class VerifyMailTemplate {

    private VerifyMailTemplate() {}

    public static String signup(
            String userName,
            String verifyUrl
    ) {
        return """
        <!DOCTYPE html>
        <html lang="ko">
        <head>
            <meta charset="UTF-8">
            <title>이메일 인증</title>
        </head>
        <body style="margin:0; padding:0; background-color:#f4f6f8;">
            <div style="max-width:600px; margin:40px auto; background:#ffffff; border-radius:8px; padding:32px; font-family:Arial, sans-serif;">
                
                <h2 style="color:#222;">안녕하세요, %s 님</h2>

                <p style="color:#555; line-height:1.6;">
                    Foongdoll 서비스 회원가입을 진행해주셔서 감사합니다.<br/>
                    아래 버튼을 클릭하시면 이메일 인증이 완료됩니다.
                </p>

                <div style="text-align:center; margin:40px 0;">
                    <a href="%s"
                       style="
                           display:inline-block;
                           padding:14px 28px;
                           background-color:#2563eb;
                           color:#ffffff;
                           text-decoration:none;
                           border-radius:6px;
                           font-size:16px;
                           font-weight:bold;
                       ">
                        이메일 인증하기
                    </a>
                </div>

                <p style="color:#888; font-size:13px;">
                    ※ 본 메일은 발신 전용입니다.<br/>
                    ※ 본인이 요청하지 않았다면 이 메일을 무시해주세요.
                </p>

                <hr style="border:none; border-top:1px solid #eee; margin:32px 0;"/>

                <p style="color:#aaa; font-size:12px;">
                    © Foongdoll. All rights reserved.
                </p>
            </div>
        </body>
        </html>
        """.formatted(userName, verifyUrl);
    }
}