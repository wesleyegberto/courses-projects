import { Component, Input, Output, EventEmitter } from '@angular/core';

@Component({
    moduleId: module.id,
    selector: 'botao',
    templateUrl: 'botao.component.html'
})
export class BotaoComponent {
    @Input() value: string;
    @Input() confirmar: boolean = false;
    @Input() textoConfirmacao: string = 'Tem certeza que deseja executar a ação?';
    @Input() classe: string = 'btn-default';
    @Input() desabilitado: boolean;
    @Output() evento = new EventEmitter();

    onClick() {
        if(this.confirmar) {
            if(!confirm(this.textoConfirmacao)) {
                return;
            }
        }
        this.evento.emit();
    }
}
