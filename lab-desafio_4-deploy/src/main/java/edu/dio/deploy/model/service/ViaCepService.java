package edu.dio.deploy.model.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import edu.dio.deploy.model.entity.Contato.dto.EnderecoRecord;

@FeignClient(name = "viacep", url = "https://viacep.com.br/ws")
public interface ViaCepService {

	@GetMapping("/{cep}/json/")
	EnderecoRecord consultarCep(@PathVariable("cep") String cep);
}
