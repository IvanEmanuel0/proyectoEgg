package com.example.proyectoEgg.service;

import com.example.proyectoEgg.entity.Cuenta;
import com.example.proyectoEgg.exception.MiException;
import com.example.proyectoEgg.repository.CuentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CuentaService implements UserDetailsService {

    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    private final String MENSAJE = "El usuario ingresado no existe %s";

    @Override
    public UserDetails loadUserByUsername(String usuario) throws UsernameNotFoundException {
        Cuenta cuenta = cuentaRepository.findByUsuario(usuario).orElseThrow(() -> new UsernameNotFoundException(String.format(MENSAJE, usuario)));
        return new User(cuenta.getUsuario(), cuenta.getClave(), Collections.emptyList());
    }

    @Transactional(readOnly = true)
    public Cuenta buscarPorId(Integer id) {
        Optional<Cuenta> cuentaOptional = cuentaRepository.findById(id);
        return cuentaOptional.orElse(null);
    }

    @Transactional
    public void crear(String usuario, String clave) {
        cuentaRepository.save(new Cuenta(usuario, encoder.encode(clave)));
    }

    @Transactional
    public void eliminar(Integer id) {
        cuentaRepository.deleteById(id);
    }

    public void habilitar(Integer id) {
        Cuenta cuenta = buscarPorId(id);
        cuenta.setAlta(true);
        cuentaRepository.save(cuenta);
    }

    public void modificar(Integer id, String usuario, String clave) {
        Cuenta cuenta = buscarPorId(id);
        cuenta.setUsuario(usuario);
        cuenta.setClave(encoder.encode(clave));
        cuentaRepository.save(cuenta);
    }

    public List<Cuenta> buscarHabilitados() {
        return cuentaRepository.usuariosHabilitados();
    }

    public List<Cuenta> buscarDeshabilitados() {
        return cuentaRepository.usuariosDeshabilitados();
    }

    public Cuenta buscarPorUsuario(String usuario) {
        Optional<Cuenta> optionalCuenta = cuentaRepository.findByUsuario(usuario);
        return optionalCuenta.orElse(null);
    }

}
