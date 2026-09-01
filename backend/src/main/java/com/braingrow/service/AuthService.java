package com.braingrow.service;

import com.braingrow.dto.AuthResponse;
import com.braingrow.dto.RegisterRequest;
import com.braingrow.dto.ResetPasswordRequest;
import com.braingrow.entity.User;
import com.braingrow.entity.VerificationCode;
import com.braingrow.repository.UserRepository;
import com.braingrow.repository.VerificationCodeRepository;
import com.braingrow.security.JwtService;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Locale;


@Service
public class AuthService {


    private final UserRepository users;

    private final VerificationCodeRepository codes;

    private final PasswordEncoder encoder;

    private final JwtService jwt;

    private final EmailService email;


    private final SecureRandom random = new SecureRandom();



    public AuthService(
            UserRepository users,
            VerificationCodeRepository codes,
            PasswordEncoder encoder,
            JwtService jwt,
            EmailService email
    ) {
        this.users = users;
        this.codes = codes;
        this.encoder = encoder;
        this.jwt = jwt;
        this.email = email;
    }



    /**
     * 用户注册
     */
    @Transactional
    public void register(RegisterRequest request) {


        String emailAddress =
                request.email()
                        .trim()
                        .toLowerCase(Locale.ROOT);



        if (users.existsByEmailIgnoreCase(emailAddress)) {

            throw new IllegalArgumentException(
                    "Email already registered"
            );
        }



        User user = new User();

        user.setEmail(emailAddress);

        user.setPasswordHash(
                encoder.encode(
                        request.password()
                )
        );

        user.setAge(
                request.age()
        );


        users.save(user);

    }





    /**
     * 用户登录
     */
    public AuthResponse login(
            com.braingrow.dto.LoginRequest request
    ) {


        String emailAddress =
                request.email()
                        .trim()
                        .toLowerCase(Locale.ROOT);



        User user =
                users.findByEmailIgnoreCase(emailAddress)
                        .orElseThrow(
                                () ->
                                new IllegalArgumentException(
                                        "Invalid email or password"
                                )
                        );



        if (
                !user.isEnabled()
                ||
                !encoder.matches(
                        request.password(),
                        user.getPasswordHash()
                )
        ) {

            throw new IllegalArgumentException(
                    "Invalid email or password"
            );

        }



        String token =
                jwt.generate(
                        user.getEmail(),
                        user.getRole().name()
                );



        return new AuthResponse(
                token,
                user.getEmail(),
                user.getRole().name()
        );

    }





    /**
     * 发送密码重置验证码
     */
    @Transactional
    public void sendResetCode(
            String emailAddress
    ) {


        String email =
                emailAddress
                        .trim()
                        .toLowerCase(Locale.ROOT);



        users.findByEmailIgnoreCase(email)
                .orElseThrow(
                        () ->
                        new IllegalArgumentException(
                                "If the account exists, a code will be sent"
                        )
                );



        codes.deleteByEmailIgnoreCaseAndPurpose(
                email,
                "RESET"
        );



        VerificationCode verification =
                new VerificationCode();



        verification.setEmail(email);

        verification.setPurpose("RESET");

        verification.setCode(
                String.format(
                        "%06d",
                        random.nextInt(1000000)
                )
        );


        verification.setExpiresAt(
                LocalDateTime.now()
                        .plusMinutes(10)
        );



        codes.save(verification);



        emailServiceSend(
                email,
                verification.getCode()
        );

    }




    /**
     * 重置密码
     */
    @Transactional
    public void reset(
            ResetPasswordRequest request
    ) {


        String email =
                request.email()
                        .trim()
                        .toLowerCase(Locale.ROOT);



        VerificationCode verification =
                codes
                .findTopByEmailIgnoreCaseAndPurposeOrderByExpiresAtDesc(
                        email,
                        "RESET"
                )
                .orElseThrow(
                        () ->
                        new IllegalArgumentException(
                                "Invalid or expired code"
                        )
                );



        if (
                verification.getExpiresAt()
                        .isBefore(LocalDateTime.now())
                ||
                !verification.getCode()
                        .equals(request.code())
        ) {

            throw new IllegalArgumentException(
                    "Invalid or expired code"
            );

        }




        User user =
                users.findByEmailIgnoreCase(email)
                        .orElseThrow(
                                () ->
                                new IllegalArgumentException(
                                        "Invalid or expired code"
                                )
                        );



        user.setPasswordHash(
                encoder.encode(
                        request.newPassword()
                )
        );



        users.save(user);



        codes.delete(
                verification
        );

    }





    /**
     * 邮件发送封装
     * 防止未来替换邮件服务影响业务
     */
    private void emailServiceSend(
            String email,
            String code
    ) {

        this.email.sendCode(
                email,
                code
        );

    }

}