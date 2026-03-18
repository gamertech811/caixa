// const data1 = document.getElementById("data")
// const data2 = document.getElementById("data2")
// const dataHoje = new Date().toISOString().split("T")[0];
//
// window.onload = () => {
//     data1.value = dataHoje
//     data2.value = dataHoje
// }
// // filha da uta do caralho de html que nao funciona essa merda de aparecer a data quando carrega desgraçade criador deaplicativo demerda odeio essa bostadelinguade porogarmacao
// window.onload = () => {
//     const dataHoje = new Date().toISOString().split("T")[0];
//
//     document.getElementById("data").value = dataHoje;
//     document.getElementById("data2").value = dataHoje;
// };
document.addEventListener("DOMContentLoaded", () => {
    const hoje = new Date().toISOString().split("T")[0];

    const data1 = document.getElementById("data");
    const data2 = document.getElementById("data2");

    if (data1) data1.value = hoje;
    if (data2) data2.value = hoje;
});

function enviar() {
     const dados = {
         data: document.getElementById("data2").value,
         valor: parseFloat(document.getElementById("valor").value),
         tipo: document.getElementById("tipo").value,
         descricao: document.getElementById("descricao").value
     };

     fetch("/api", {
         method: "POST",
         headers: {
             "Content-Type": "application/json"
         },
         body: JSON.stringify(dados)
     })
         .then(r => r.json())
         .then(d => {

         console.log("Salvo:", d);
         window.location.reload()
         })
         .catch(e => console.error(e));
 }
function buscar(){
    const data = document.getElementById("data").value;
    if (!data){
        alert("data faltaodo")
    }

    fetch(`/api/extrato?data=${data}`)
        .then(r => r.json())
        .then(d => {
            const tabela= document.getElementById("tabela")
            tabela.innerHTML = "";

            if (d.length === 0){
                alert("nenhum movimento nesta data")
                return;
            }
            let saldo = 0

            d.forEach(m => {
                saldo += m.tipo === 1 ? m.valor : -m.valor;
                const tipo = m.tipo === 1 ? "lime": "red"
                const linha = `
                    <tr>
                        <td>${m.descricao}</td>
                        <td style="color: ${tipo}">${m.valor.toFixed(2)}</td>
                        <td>${m.data}</td>
                    </tr>
                `;
                tabela.innerHTML += linha;
            })
            const linhaFinal = `
                            <tr style="font-weight: bold; border-top: 2px solid white;">
                                <td>Saldo final</td>
                                <td>${saldo.toFixed(2)}</td>
                                <td></td>
                            </tr>
                        `;
            tabela.innerHTML += linhaFinal;
        })
        .catch(e =>console.error(e))
}

function gerarPDF() {
    const data = document.getElementById("data").value;

    if (!data) {
        alert("data faltando");
        return;
    }

    fetch(`/api/gerarpdf?data=${data}`)
        .then(response => {
            if (!response.ok) {
                throw new Error("Erro ao gerar PDF");
            }
            return response.blob();
        })
        .then(blob => {
            const url = window.URL.createObjectURL(blob);
            window.open(url); // abre o PDF
        })
        .catch(e => console.error(e));
}