import { Component, Input } from '@angular/core';

@Component({
    moduleId: module.id,
    selector: 'mensagem',
    templateUrl: 'mensagem.component.html'
})
export class MensagemComponent {
    @Input() erro: boolean;
    @Input() mensagem: string;

    get classeCss(): string {
        return this.erro ? 'danger' : 'info';
    }
}
