package br.com.branetlogistica.core.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import br.com.branetlogistica.core.context.ContextUtil;
import jakarta.servlet.DispatcherType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class InterceptorRequest implements HandlerInterceptor {
	
	private final ContextUtil contextUtil;
	private final PosInterceptor posInterceptor;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {

		if (request.getDispatcherType() != DispatcherType.REQUEST)
			return true;

		contextUtil.createContext(request, object);
		
		
		
		return true;
		

	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {			
		posInterceptor.posAction(response.getStatus());
		
	}

}