package com.apirest.TCBackEnd.Endpoint;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apirest.TCBackEnd.Controle.UsuarioControle;
import com.apirest.TCBackEnd.DTO.UsuarioDTO;
import com.apirest.TCBackEnd.Models.Usuario;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/api/usuario")
@Api(value = "API REST Usuario")
@CrossOrigin(origins = "*")

public class UsuarioEndPoint {

	@Autowired
	UsuarioControle usuarioControle;

	@ApiOperation(value = "Retorna uma lista de Usuarios")
	@GetMapping("")
	public ResponseEntity<?> listarTodos() {
		return new ResponseEntity<>(listarResposta(usuarioControle.listarTodos()), HttpStatus.OK);
	}

	@ApiOperation(value = "Retorna um Usuario unico pelo ID")
	@GetMapping("/{id}")
	public ResponseEntity<?> listar(@PathVariable(value = "id") long id) {
		Optional<Usuario> user = usuarioControle.listar(id);
		if (user == null) {
			// throw new ResourceNotFoundException("Usuario n encontrado para ID : " + id);
		}
		return new ResponseEntity<>(usuarioResposta(user.get()), HttpStatus.OK);
	}

	@ApiOperation(value = "Salva um Usuario")
	@PostMapping("")
	public ResponseEntity<?> salvar(@RequestBody UsuarioDTO usuarioDTO) {
		Usuario usuario = usuarioControle.salvar(usuarioDTO);
		return new ResponseEntity<>(usuarioResposta(usuario), HttpStatus.CREATED);
	}

	// ----------- METODOS AUXILIARES -------------------------
	private UsuarioDTO usuarioResposta(Usuario usuario) {
		return new UsuarioDTO(usuario.getId(), usuario.getRole().getNameRole(), usuario.getNome(), usuario.getCpf(),
				usuario.getTelefone(), usuario.getWhatsapp(), usuario.getEmail(), usuario.getSenha());
	}

	// Recebe uma lista de usuarios e transforma a lista para o formato de resposta
	private Iterable<UsuarioDTO> listarResposta(Iterable<Usuario> listaUsuarios) {
		// Cria a lista que sera retornada
		List<UsuarioDTO> listaDTO = new ArrayList<UsuarioDTO>();
		// Faz um for na lista recebida no metodo
		for (Usuario usuario : listaUsuarios) {
			listaDTO.add(usuarioResposta(usuario));
		}
		return listaDTO;
	}

}
