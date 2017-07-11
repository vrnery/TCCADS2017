package br.edu.ifrs.restinga.tcc.vrnery.sipveicular.controller;

public class ClientePlacasWSControllerTest {

	public static void main(String[] args) {
		ClientePlacasWSController clienteControle = new ClientePlacasWSController("BJY0987");
		System.out.printf("Cod: %d\t\tPlaca: %s\t\tSituação: %s\nDados: %s\t\tData da consulta: %s",
				clienteControle.getPlResponse().getId(), clienteControle.getPlResponse().getPlaca(),
				clienteControle.getPlResponse().getSituacao(), clienteControle.getPlResponse().getDados(),
				clienteControle.getPlResponse().getData());
	}

}
