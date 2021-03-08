package br.com.alura.api.Forum.Alura.configuration.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import br.com.alura.api.Forum.Alura.repository.UsuarioRepository;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private AutenticacaoService autenticacaoService;
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Override
	@Bean
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}

	// configuracoes de autenticação
	// aqui usamos os algoritmos de hash
		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth.userDetailsService(autenticacaoService).passwordEncoder(new BCryptPasswordEncoder());
		}
		
		//configuracoes de autorização
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.authorizeRequests()
			.antMatchers(HttpMethod.GET, "/topicos").permitAll() // permitindo a url topicos
			.antMatchers(HttpMethod.GET, "/topicos/*").permitAll() // permitindo tudo em tópicos
			.antMatchers(HttpMethod.POST, "/auth").permitAll() // permitindo a url auth
			.antMatchers(HttpMethod.GET, "/actuator/*").permitAll() // permitindo a url do método actuator
			.anyRequest().authenticated()
			.and().csrf().disable() // csrf é um tipo de ataque hacker
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // política de criação de sessao
			.and().addFilterBefore(new AutenticacaoViaTokenFilter(tokenService, usuarioRepository), UsernamePasswordAuthenticationFilter.class);
		}
		
		//configuracao de recursos estáticos(css, js, imagens)
		@Override
		public void configure(WebSecurity web) throws Exception {
			web.ignoring().antMatchers("/**.html", "/v2/api-docs", "/webjars/**", "/configuration/**", "/swagger-resourses");
		}
		
}
