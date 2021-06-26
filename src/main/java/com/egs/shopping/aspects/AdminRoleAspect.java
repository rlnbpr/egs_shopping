package com.egs.shopping.aspects;

import com.egs.shopping.domain.Customer;
import com.egs.shopping.domain.enumeration.CustomerRoles;
import com.egs.shopping.service.SecurityService;
import com.egs.shopping.service.exception.InvalidAuthorizationException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class AdminRoleAspect {

    private final Logger log = LoggerFactory.getLogger(AdminRoleAspect.class);

    private final SecurityService securityService;

    public AdminRoleAspect(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Around("@annotation(AdminRole)")
    public Object checkAdminRole(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
            .getRequestAttributes()).getRequest();
        String token = request.getHeader("token");
        log.debug("token in header: {}", token);
        if(token == null || token.isEmpty()) {
            throw new InvalidAuthorizationException();
        }
        Customer customer = securityService.parseToken(token);
        if(!customer.getRole().equals(CustomerRoles.ADMIN)) {
            throw new InvalidAuthorizationException();
        }
        return joinPoint.proceed();
    }
}
