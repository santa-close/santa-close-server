import { Controller, Get } from '@nestjs/common';
import { SantaCloseAppService } from './santa-close-app.service';

@Controller()
export class SantaCloseAppController {
  constructor(private readonly santaCloseAppService: SantaCloseAppService) {}

  @Get()
  getHello(): string {
    return this.santaCloseAppService.getHello();
  }
}
