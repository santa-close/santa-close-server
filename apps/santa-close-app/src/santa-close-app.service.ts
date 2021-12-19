import { Injectable } from '@nestjs/common';

@Injectable()
export class SantaCloseAppService {
  getHello(): string {
    return 'Hello World!';
  }
}
