package in.kb.main.services;

import in.kb.main.entitys.RefreshToken;

public interface RefreshTokenServices {

    public RefreshToken createRefreshToken(String email);
    public RefreshToken verifyRefreshToken(String refreshToken);

}
