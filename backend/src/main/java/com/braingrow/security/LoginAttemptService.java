package com.braingrow.security;
import org.springframework.beans.factory.annotation.Value; import org.springframework.stereotype.Service; import java.time.*; import java.util.concurrent.ConcurrentHashMap;
@Service public class LoginAttemptService {
 private record Attempt(int failures,Instant lockedUntil){}
 private final ConcurrentHashMap<String,Attempt> attempts=new ConcurrentHashMap<>(); private final int maxFailures; private final Duration lock;
 public LoginAttemptService(@Value("${app.login-max-failures:10}") int max,@Value("${app.login-lock-minutes:15}") long minutes){maxFailures=max;lock=Duration.ofMinutes(minutes);}
 public boolean isLocked(String email){Attempt a=attempts.get(email); if(a==null)return false; if(a.lockedUntil()!=null&&a.lockedUntil().isAfter(Instant.now()))return true; if(a.lockedUntil()!=null)attempts.remove(email); return false;}
 public void success(String email){attempts.remove(email);}
 public void failure(String email){attempts.compute(email,(k,a)->{int n=a==null?1:a.failures()+1;return n>=maxFailures?new Attempt(n,Instant.now().plus(lock)):new Attempt(n,null);});}
}
