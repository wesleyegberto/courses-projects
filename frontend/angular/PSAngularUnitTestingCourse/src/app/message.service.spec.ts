import { MessageService } from './message.service';

describe('MessageService', () => {
  let cut: MessageService = null;

  beforeEach(() => {
    cut = new MessageService();
  });

  it('should have no message to start', () => {
    expect(cut.messages.length).toBe(0);
  });

  it('should add a message when add is called', () => {
    cut.add('message1');

    expect(cut.messages.length).toBe(1);
    const storedMessage = cut.messages[0];
    expect(storedMessage).toBe('message1');
  });

  it('should remove all messages when clear is called', () => {
    cut.add('message to remove');

    cut.clear();

    expect(cut.messages.length).toBe(0);
  });
});
