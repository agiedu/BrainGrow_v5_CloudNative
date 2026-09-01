package com.braingrow.security;
import jakarta.servlet.*; import jakarta.servlet.http.*; import org.springframework.security.authentication.UsernamePasswordAuthenticationToken; import org.springframework.security.core.authority.SimpleGrantedAuthority; import org.springframework.security.core.context.SecurityContextHolder; import org.springframework.stereotype.Component; import org.springframework.web.filter.OncePerRequestFilter; import java.io.IOException; import java.util.List;
@Component public class JwtAuthenticationFilter extends OncePerRequestFilter {
 private final JwtService jwt; public JwtAuthenticationFilter(JwtService jwt){this.jwt=jwt;}
 @Override protected void doFilterInternal(HttpServletRequest req,HttpServletResponse res,FilterChain chain)throws ServletException,IOException{
   String h=req.getHeader("Authorization");
   if(h!=null&&h.startsWith("Bearer ")){String t=h.substring(7).trim(); if(!t.isBlank()&&jwt.valid(t)){try{
      String role=jwt.role(t); if(role!=null){var a=new UsernamePasswordAuthenticationToken(jwt.username(t),null,List.of(new SimpleGrantedAuthority("ROLE_"+role))); SecurityContextHolder.getContext().setAuthentication(a);}
   }catch(Exception ignored){}}}
   chain.doFilter(req,res);
 }
}
