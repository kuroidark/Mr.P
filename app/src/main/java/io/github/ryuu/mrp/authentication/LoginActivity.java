package io.github.ryuu.mrp.authentication;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.widget.Toast;

import java.security.KeyStore;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import androidx.appcompat.app.AppCompatActivity;
import io.github.ryuu.mrp.R;
import io.github.ryuu.mrp.data.DataFile;
import io.github.ryuu.mrp.userinterface.Home;

public class LoginActivity extends AppCompatActivity {

    private static final String DEFAULT_KEY_NAME = "default_key";

    /**
     * 如果密码校验通过
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            onAuthenticated();
        } else {
            finish();
        }
    }

    KeyStore keyStore;

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        DataFile.verifyStoragePermissions(LoginActivity.this);
        if (supportFingerprint()) {
            initKey();
            initCipher();
        }
    }

    public void usePsd() {
        KeyguardLockScreenManager keyguardLockScreenManager = new KeyguardLockScreenManager(LoginActivity.this);
        if (keyguardLockScreenManager.isOpenLockScreenPwd()) {
            keyguardLockScreenManager.showAuthenticationScreen(this);
        } else {
            Toast.makeText(this, "请至少设置一个密码,以便本app工作", Toast.LENGTH_LONG).show();
        }
    }

    public boolean supportFingerprint() {
        if (Build.VERSION.SDK_INT < 23) {
            //Toast.makeText(this, "您的系统版本过低，不支持指纹功能", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            KeyguardManager keyguardManager = getSystemService(KeyguardManager.class);
            FingerprintManager fingerprintManager = getSystemService(FingerprintManager.class);
            if (!fingerprintManager.isHardwareDetected()) {
                //Toast.makeText(this, "您的手机不支持指纹功能", Toast.LENGTH_SHORT).show();
                usePsd();
                return false;
            } else if (!keyguardManager.isKeyguardSecure()) {
                usePsd();
//                Toast.makeText(this, "您还未设置锁屏，请先设置锁屏并添加一个指纹", Toast.LENGTH_SHORT).show();
                return false;
            } else if (!fingerprintManager.hasEnrolledFingerprints()) {
                usePsd();
                //Toast.makeText(this, "您至少需要在系统设置中添加一个指纹", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }

    @TargetApi(23)
    private void initKey() {
        try {
            keyStore = KeyStore.getInstance("AndroidKeyStore");
            keyStore.load(null);
            KeyGenerator keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
            KeyGenParameterSpec.Builder builder = new KeyGenParameterSpec.Builder(DEFAULT_KEY_NAME,
                    KeyProperties.PURPOSE_ENCRYPT |
                            KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7);
            keyGenerator.init(builder.build());
            keyGenerator.generateKey();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @TargetApi(23)
    private void initCipher() {
        try {
            SecretKey key = (SecretKey) keyStore.getKey(DEFAULT_KEY_NAME, null);
            Cipher cipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/"
                    + KeyProperties.BLOCK_MODE_CBC + "/"
                    + KeyProperties.ENCRYPTION_PADDING_PKCS7);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            showFingerPrintDialog(cipher);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void showFingerPrintDialog(Cipher cipher) {
        FingerprintDialogFragment fragment = new FingerprintDialogFragment();
        fragment.setCancelable(false);
        fragment.setCipher(cipher);
        fragment.show(getFragmentManager(), "fingerprint");
    }

    public void onAuthenticated() {
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
        finish();
    }
}

class KeyguardLockScreenManager {
    public final static int REQUEST_CODE_CONFIRM_DEVICE_CREDENTIALS = 0;
    private KeyguardManager mKeyManager;

    /**
     * 是否开启锁屏密码，注意：有Api版本限制
     *
     * @return
     */
    public boolean isOpenLockScreenPwd() {
        try {
            if (Build.VERSION.SDK_INT < 16) {
                return false;
            }
            return mKeyManager != null && mKeyManager.isKeyguardSecure();
        } catch (Exception e) {
            return false;
        }
    }

    public KeyguardLockScreenManager(Context context) {
        mKeyManager = getKeyguardManager(context);
    }

    public static KeyguardManager getKeyguardManager(Context context) {
        KeyguardManager keyguardManager = null;
        try {
            keyguardManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
        } catch (Throwable throwable) {
//        FPLog.log("getKeyguardManager exception");
        }
        return keyguardManager;
    }

    /**
     * 锁屏密码，注意：有Api版本限制
     */
    public void showAuthenticationScreen(Activity activity) {
        if (Build.VERSION.SDK_INT < 21) {
            return;
        }
        Intent intent = mKeyManager.createConfirmDeviceCredentialIntent("确认你的身份", "使用你的锁屏密码或指纹");
        if (intent != null) {
            activity.startActivityForResult(intent, REQUEST_CODE_CONFIRM_DEVICE_CREDENTIALS);
        }

    }

    public void onDestroy() {
        mKeyManager = null;
    }
}
