export class DateHelper {
    constructor() {
        // não faz sentido criar uma instância
        throw new Erro('DateHelper não pode ser instanciada');
    }

    static dataParaTexto(data) {
        return `${data.getDate()}/${data.getMonth() + 1}/${data.getFullYear()}`;
    }

    static textoParaData(texto) {
        if (!/\d{2}\/\d{2}\/\d{4}/g.test(texto)) {
            throw new Error('Deve estar no formato dd/mm/aaaa');
        }
        // ES6 - spread operator = cada posição do array vira um argumento
        return new Date(...texto.split('/').reverse().map((item, indice) => item - indice % 2));
    }
}