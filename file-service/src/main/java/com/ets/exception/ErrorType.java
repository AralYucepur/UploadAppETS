package com.ets.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum ErrorType{

    INTERNAL_ERROR(5100,"Sunucuda beklenmeyen hata oluştu.",HttpStatus.INTERNAL_SERVER_ERROR),
    REGISTER_USED_USERNAME_ERROR(4110,"Kullanıcı adı kullanılıyor.",HttpStatus.BAD_REQUEST),
    REGISTER_UNMATCHED_REPASSWORD_ERROR(4111,"Şifre ve şifre tekrarı aynı olmalı.",HttpStatus.BAD_REQUEST),
    LOGIN_ERROR(4112,"Kullanıcı adı veya şifre hatalı.",HttpStatus.BAD_REQUEST),
    JWT_TOKEN_CREATE_ERROR(4113,"Token oluşturulamadı.",HttpStatus.BAD_REQUEST),
    BAD_REQUEST_ERROR(4100,"Parametre eksik veya hatalı.",HttpStatus.BAD_REQUEST),
    JWT_INVALID_TOKEN(4114,"Geçersiz token.",HttpStatus.BAD_REQUEST),
    INVALID_AUTHORITY(4115,"Yetkisiz Kullanıcı.",HttpStatus.BAD_REQUEST),
    INACTIVE_ACCOUNT(4116,"Hesabınız aktif değil. Lütfen Hesabınızı mail adresine gelen kodu kullanarak aktifleştiriniz",HttpStatus.BAD_REQUEST),
    BLOCKED_ACCOUNT(4117,"Hesabınız yasaklanmıştır.",HttpStatus.BAD_REQUEST),
    WRONG_ACTIVATIONCODE(4118,"Yanlış aktivasyon kodu.",HttpStatus.BAD_REQUEST),
    UPLOAD_ERROR(4210,"Dosya yüklenirken hata oluştu.",HttpStatus.BAD_REQUEST),
    ;


    int code;
    String message;
    HttpStatus httpStatus;

}
