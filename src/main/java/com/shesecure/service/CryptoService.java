package com.shesecure.service;
import javax.crypto.Cipher; import javax.crypto.spec.GCMParameterSpec; import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer; import java.security.SecureRandom; import java.util.Base64; import org.springframework.stereotype.Service;
@Service
public class CryptoService {
 private final byte[] key;
 public CryptoService(){
  String configured=System.getenv("SHESecure_AES_KEY");
  if(configured==null || configured.isBlank()) throw new IllegalStateException("Set SHESecure_AES_KEY to a Base64-encoded 32-byte key before starting.");
  key=Base64.getDecoder().decode(configured);
  if(key.length!=32) throw new IllegalStateException("SHESecure_AES_KEY must decode to exactly 32 bytes.");
 }
 public byte[] encrypt(byte[] input) throws Exception {
  byte[] iv=new byte[12]; new SecureRandom().nextBytes(iv);
  Cipher c=Cipher.getInstance("AES/GCM/NoPadding"); c.init(Cipher.ENCRYPT_MODE,new SecretKeySpec(key,"AES"),new GCMParameterSpec(128,iv));
  byte[] encrypted=c.doFinal(input); return ByteBuffer.allocate(iv.length+encrypted.length).put(iv).put(encrypted).array();
 }
 public byte[] decrypt(byte[] input) throws Exception {
  ByteBuffer b=ByteBuffer.wrap(input); byte[] iv=new byte[12]; b.get(iv); byte[] data=new byte[b.remaining()]; b.get(data);
  Cipher c=Cipher.getInstance("AES/GCM/NoPadding"); c.init(Cipher.DECRYPT_MODE,new SecretKeySpec(key,"AES"),new GCMParameterSpec(128,iv)); return c.doFinal(data);
 }
}
