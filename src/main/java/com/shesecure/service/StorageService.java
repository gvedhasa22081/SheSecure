package com.shesecure.service;
import org.springframework.stereotype.Service; import java.nio.file.*; import java.util.UUID;
@Service
public class StorageService {
 private final CryptoService crypto;
 private final Path root=Paths.get(System.getenv().getOrDefault("SHESecure_STORAGE","./private-evidence")).toAbsolutePath().normalize();
 public StorageService(CryptoService crypto)throws Exception{this.crypto=crypto;Files.createDirectories(root);}
 public String save(byte[] data)throws Exception{String id=UUID.randomUUID().toString()+".enc";Files.write(root.resolve(id),crypto.encrypt(data),StandardOpenOption.CREATE_NEW);return id;}
 public byte[] read(String id)throws Exception{
  Path p=root.resolve(id).normalize(); if(!p.getParent().equals(root))throw new SecurityException("Invalid file key");
  return crypto.decrypt(Files.readAllBytes(p));
 }
 public void delete(String id)throws Exception{Path p=root.resolve(id).normalize();if(p.getParent().equals(root))Files.deleteIfExists(p);}
}
