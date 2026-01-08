package com.foongdoll.portfolio.backend.core.util.mail.template;

public final class VerifyMailTemplate {

    private VerifyMailTemplate() {}

    public static String signup(String userName, String verifyUrl) {
        return """
        <!DOCTYPE html>
        <html lang="ko">
        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Foongdoll 이메일 인증</title>
        </head>
        <body style="margin:0; padding:0; background-color:#f3f4f6;">
            <table width="100%%" cellpadding="0" cellspacing="0" style="background-color:#f3f4f6; padding:40px 0;">
                <tr>
                    <td align="center">
                        <table width="600" cellpadding="0" cellspacing="0"
                               style="background:#ffffff; border-radius:12px; box-shadow:0 10px 25px rgba(0,0,0,0.05); overflow:hidden; font-family:Apple SD Gothic Neo, Arial, sans-serif;">
                            
                            <!-- Header -->
                            <tr>
                                <td style="background:#111827; padding:28px 32px;">
                                    <h1 style="margin:0; font-size:20px; color:#ffffff; letter-spacing:0.5px;">
                                        Foongdoll
                                    </h1>
                                </td>
                            </tr>
    
                            <!-- Content -->
                            <tr>
                                <td style="padding:36px 32px;">
                                    <h2 style="margin:0 0 12px; color:#111827; font-size:22px;">
                                        안녕하세요, %s 님 👋
                                    </h2>
    
                                    <p style="margin:0 0 20px; color:#4b5563; line-height:1.7; font-size:15px;">
                                        Foongdoll 서비스 회원가입을 진행해 주셔서 감사합니다.<br/>
                                        아래 <strong>이메일 인증 버튼</strong>을 클릭하시면 회원가입이 완료됩니다.
                                    </p>
    
                                    <!-- Button -->
                                    <div style="text-align:center; margin:36px 0;">
                                        <a href="%s"
                                           style="
                                               display:inline-block;
                                               padding:14px 36px;
                                               background:linear-gradient(135deg,#2563eb,#1d4ed8);
                                               color:#ffffff;
                                               text-decoration:none;
                                               border-radius:999px;
                                               font-size:15px;
                                               font-weight:600;
                                           ">
                                            이메일 인증 완료하기
                                        </a>
                                    </div>
    
                                    <!-- Fallback -->
                                    <p style="margin:32px 0 0; color:#6b7280; font-size:13px; line-height:1.6;">
                                        버튼이 클릭되지 않는 경우 아래 링크를 복사하여 브라우저에 붙여넣어 주세요.
                                    </p>
    
                                    <p style="word-break:break-all; font-size:12px; color:#2563eb;">
                                        %s
                                    </p>
    
                                    <p style="margin-top:28px; color:#9ca3af; font-size:12px;">
                                        본 메일은 발신 전용 메일입니다.<br/>
                                        본인이 요청하지 않았다면 이 메일을 무시해 주세요.
                                    </p>
                                </td>
                            </tr>
    
                            <!-- Footer -->
                            <tr>
                                <td style="background:#f9fafb; padding:20px 32px; text-align:center;">
                                    <p style="margin:0; font-size:12px; color:#9ca3af;">
                                        © 2026 Foongdoll. All rights reserved.
                                    </p>
                                </td>
                            </tr>
    
                        </table>
                    </td>
                </tr>
            </table>
        </body>
        </html>
    """.formatted(userName, verifyUrl, verifyUrl);
    }

    public static String companyInviteUrl(String companyNm, String inviteUrl, String expire, String supportEmail){
        return """
                <!doctype html>
                <html lang="ko">
                  <head>
                    <meta charset="utf-8" />
                    <meta name="viewport" content="width=device-width,initial-scale=1" />
                    <title>WORK-HUB 회사 초대</title>
                  </head>
                  <body style="margin:0;padding:0;background:#f6f7fb;font-family:-apple-system,BlinkMacSystemFont,'Segoe UI',Roboto,'Noto Sans KR','Apple SD Gothic Neo','Malgun Gothic',Arial,sans-serif;color:#111827;">
                    <table role="presentation" cellpadding="0" cellspacing="0" border="0" width="100%" style="background:#f6f7fb;">
                      <tr>
                        <td align="center" style="padding:32px 16px;">
                          <table role="presentation" cellpadding="0" cellspacing="0" border="0" width="600" style="max-width:600px;width:100%;background:#ffffff;border-radius:16px;overflow:hidden;box-shadow:0 8px 24px rgba(17,24,39,0.08);">
                            
                            <!-- Header -->
                            <tr>
                              <td style="padding:28px 28px 18px 28px;background:linear-gradient(135deg,#4f46e5,#7c3aed);color:#ffffff;">
                                <div style="font-weight:800;font-size:16px;letter-spacing:0.6px;">WORK-HUB</div>
                                <div style="margin-top:10px;font-size:22px;font-weight:800;line-height:1.25;">
                                  회사 초대 링크가 생성되었어요
                                </div>
                                <div style="margin-top:8px;font-size:13px;opacity:0.9;line-height:1.5;">
                                  승인 완료된 회사로 멤버를 초대할 수 있습니다.
                                </div>
                              </td>
                            </tr>
                
                            <!-- Body -->
                            <tr>
                              <td style="padding:24px 28px 8px 28px;">
                                <div style="font-size:14px;line-height:1.7;color:#111827;">
                                  <div style="margin:0 0 10px 0;">
                                    <b style="font-size:15px;">{companyName}</b> 회사가 <b>승인</b>되었습니다.
                                  </div>
                                  <div style="margin:0 0 14px 0;color:#374151;">
                                    아래 버튼을 통해 초대 링크로 이동할 수 있어요.
                                    (링크는 외부 공유 가능하므로, 필요한 사람에게만 전달해 주세요.)
                                  </div>
                
                                  <!-- Button -->
                                  <div style="margin:18px 0 14px 0;">
                                    <a href="{inviteUrl}"
                                       style="display:inline-block;background:#111827;color:#ffffff;text-decoration:none;padding:12px 18px;border-radius:12px;font-weight:700;font-size:14px;">
                                      초대 링크 열기
                                    </a>
                                  </div>
                
                                  <!-- Fallback -->
                                  <div style="margin:0 0 14px 0;font-size:12px;color:#6b7280;line-height:1.6;">
                                    버튼이 동작하지 않으면 아래 주소를 복사해 브라우저에 붙여넣어 주세요.<br/>
                                    <span style="word-break:break-all;color:#374151;">{inviteUrl}</span>
                                  </div>
                
                                  <!-- Meta -->
                                  <table role="presentation" cellpadding="0" cellspacing="0" border="0" width="100%"
                                         style="margin-top:16px;background:#f9fafb;border:1px solid #e5e7eb;border-radius:12px;">
                                    <tr>
                                      <td style="padding:12px 14px;">
                                        <div style="font-size:12px;color:#6b7280;line-height:1.7;">
                                          <div><b style="color:#111827;">만료</b>: {expiresAt}</div>
                                          <div><b style="color:#111827;">보안 안내</b>: 본 메일은 자동 발송이며, 본인이 요청하지 않았다면 무시해 주세요.</div>
                                        </div>
                                      </td>
                                    </tr>
                                  </table>
                
                                  <div style="margin-top:16px;font-size:12px;color:#6b7280;line-height:1.7;">
                                    도움이 필요하면 <a href="mailto:{supportEmail}" style="color:#4f46e5;text-decoration:none;">{supportEmail}</a> 로 문의해 주세요.
                                  </div>
                                </div>
                              </td>
                            </tr>
                
                            <!-- Footer -->
                            <tr>
                              <td style="padding:18px 28px 26px 28px;">
                                <div style="border-top:1px solid #eef2f7;margin-top:14px;padding-top:14px;font-size:11px;color:#9ca3af;line-height:1.6;">
                                  © WORK-HUB. All rights reserved.<br/>
                                  이 메일은 회사 승인 처리에 따라 자동 발송되었습니다.
                                </div>
                              </td>
                            </tr>
                
                          </table>
                        </td>
                      </tr>
                    </table>
                  </body>
                </html>
                """;
    }
}