# Mr.P

这是一个基础的账户管理工具，仅支持本地备份和恢复，数据库使用AES加密



加密和解密秘钥在 `io.github.ryuu.mrp.authentication.AESUtil` 中 `encrypt(String string)` 和 `decrypt(String string)` 处修改