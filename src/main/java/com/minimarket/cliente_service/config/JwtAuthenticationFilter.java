package com.minimarket.cliente_service.config;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{
    @Autowired // puedo ponerle requiredargscosntructor?
    private JwtService jwtService;
    //importarrrrr
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
         HttpServletResponse response,
          FilterChain filterChain
    ) throw ServletException{
        String authHeader = request.getHeader("Authorization");
    }

    if (authHeader == null | !authHeader.startsWith("Bearer")){
        filterChain.doFilter(request,response);
        return;
    }

    String jwt = authHeader.substring(7);

    String username = jwtService.extraerUsername(jwt);

    if(usermane != null && SecurityContextHolder.getContext().getAuthentication() == null){
        UserDetails usuario = userDetailsService.loadUserByUsername(username);
    }

}
