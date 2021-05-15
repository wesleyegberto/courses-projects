import { Injectable } from '@angular/core';
import { MessageService } from './message.service';

@Injectable()
export class CalculationService {
  constructor(private readonly messageService: MessageService) {}

  calculate(): number {
    this.messageService.add('Calculation result: 42');
    return 42;
  }
}
