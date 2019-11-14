$(document).ready(
	function () {

		function limpa_formulario_cep() {
			// Limpa valores do formulario de cep.
			$("#txtStreet").val("");
			$("#txtDistrict").val("");
			$("#txtCity").val("");
			$("#txtFederalUnit").val("");
		}

		// Quando o campo cep perde o foco.
		$("#txtZip").blur(
			function () {

				// Nova variavel "cep" somente com digitos.
				var cep = $(this).val().replace(/\D/g, '');

				// Verifica se campo cep possui valor informado.
				if (cep != "") {

					// Expressao regular para validar o CEP.
					var validacep = /^[0-9]{8}$/;

					// Valida o formato do CEP.
					if (validacep.test(cep)) {

						// Preenche os campos com "..." enquanto
						// consulta webservice.
						$("#txtStreet").val("...");
						$("#txtDistrict").val("...");
						$("#txtCity").val("...");
						$("#txtFederalUnit").val("...");

						// Consulta o webservice viacep.com.br/
						$.getJSON("https://viacep.com.br/ws/" + cep
							+ "/json/?callback=?", function (dados) {

								if (!("erro" in dados)) {
									// Atualiza os campos com os valores da
									// consulta.
									$("#txtStreet").val(dados.logradouro);
									$("#txtDistrict").val(dados.bairro);
									$("#txtCity").val(dados.localidade);
									$("#txtFederalUnit").val(dados.uf);
								} // end if.
								else {
									// CEP pesquisado nao foi encontrado.
									limpa_formulario_cep();
									alert("CEP nao encontrado.");
								}
							});
					} else {
						// cep invalido.
						limpa_formulario_cep();
						alert("Formato de CEP invalido.");
					}
				} else {
					// cep sem valor, limpa formulrio.
					limpa_formulario_cep();
				}
			});
	}
);