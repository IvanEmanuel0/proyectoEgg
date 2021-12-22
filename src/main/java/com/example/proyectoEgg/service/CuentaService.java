package com.example.proyectoEgg.service;

import com.example.proyectoEgg.entity.Cuenta;

import com.example.proyectoEgg.entity.Rol;
import com.example.proyectoEgg.exception.MiException;
import com.example.proyectoEgg.repository.CuentaRepository;
import com.example.proyectoEgg.utilities.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CuentaService implements UserDetailsService {

    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private RolService rolService;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private EmailService emailService;

    private final String MENSAJE = "El usuario ingresado no existe %s";

    @Override
    public UserDetails loadUserByUsername(String usuario) throws UsernameNotFoundException {
        Cuenta cuenta = cuentaRepository.findByUsuario(usuario).orElseThrow(() -> new UsernameNotFoundException(String.format(MENSAJE, usuario)));
        //Cuenta cuenta = cuentaRepository.buscarCuentaPorUsuario(usuario);
        //if(cuenta == null) throw new UsernameNotFoundException(String.format(MENSAJE, usuario));
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + cuenta.getRol().getNombre());

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attributes.getRequest().getSession(true);

        session.setAttribute("idSession", cuenta.getId());

        return new User(cuenta.getUsuario(), cuenta.getClave(), Collections.singletonList(authority));
    }

    @Transactional(readOnly = true)
    public Cuenta buscarPorId(Integer id) throws MiException{
        try{
            Util.esNumero(Integer.toString(id));
        }catch(MiException e){
            throw e;
        }
        Optional<Cuenta> cuentaOptional = cuentaRepository.findById(id);
        return cuentaOptional.orElse(null);
    }

    @Transactional
    public void crear(String usuario, String clave, String correo) throws MiException {

        if(cuentaRepository.existsCuentaByUsuario(usuario)){
        throw new MiException("Ya existe el usuario ingresado.");
        }
            Util.validarUsuario(usuario);
            Util.validarClave(clave);
            //Util.validarEmail(correo);

            Cuenta cuenta = new Cuenta();
            cuenta.setUsuario(usuario);
            cuenta.setClave(encoder.encode(clave));
            cuenta.setCorreo(correo);

            Rol rol;

            if (cuentaRepository.findAll().isEmpty()) {
                rol = rolService.buscarRolPorNombre("ADMIN");

            }else{
                rol = rolService.buscarRolPorNombre("USER");
            }
            cuenta.setRol(rol);
            cuenta.setAlta(true);
            cuentaRepository.save(cuenta);
            emailService.enviar(correo);
            }

    @Transactional
    public void eliminar(Integer id) throws MiException {
        try {
            Util.esNumero(Integer.toString(id));
            cuentaRepository.deleteById(id);

        } catch (MiException e){
            throw e;
        }

    }

    public void habilitar(Integer id) throws MiException{
        try {
            Util.esNumero(Integer.toString(id));
            Cuenta cuenta = buscarPorId(id);
            cuenta.setAlta(true);
            cuentaRepository.save(cuenta);
        } catch (MiException e) {
            throw e;
        }
    }

    public void modificar(Integer id, String clave) throws MiException {

        try {
            //Util.validarUsuario(usuario); //verificar
            Util.validarClave(clave);
            Cuenta cuenta = buscarPorId(id);
            if(cuenta != null){
                cuenta.setId(id);
                //cuenta.setUsuario(usuario);
                cuenta.setClave(encoder.encode(clave));
                cuentaRepository.save(cuenta);
            }
        }catch (MiException e){
            throw e;
        }catch (Exception e){
            throw e;
        }

        /*try {
            Util.esNumero(Integer.toString(id));
            Cuenta cuenta = buscarPorId(id);
            cuenta.setUsuario(usuario);
            cuenta.setClave(encoder.encode(clave));
            cuentaRepository.save(cuenta);
        } catch (MiException e) {
            throw e;
        }*/

    }

    @Transactional
    public void actualizarRol(Integer id) throws MiException{
        try {
            Cuenta cuenta = buscarPorId(id);
            if(cuenta != null){
                cuenta.setId(id);
                cuenta.setRol(rolService.buscarRolPorNombre("USERPRO"));
                cuentaRepository.save(cuenta);
            }
        }catch (MiException e){
            throw e;
        }catch (Exception e){
            throw e;
        }
    }

    @Transactional
    public List<Cuenta> buscarHabilitados() {
        return cuentaRepository.usuariosHabilitados();
    }

    @Transactional
    public List<Cuenta> buscarDeshabilitados() {
        return cuentaRepository.usuariosDeshabilitados();
    }

    @Transactional
    public Cuenta buscarPorUsuario(String usuario) {
        Optional<Cuenta> optionalCuenta = cuentaRepository.findByUsuario(usuario);
        return optionalCuenta.orElse(null);
    }

}