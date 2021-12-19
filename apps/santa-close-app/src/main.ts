import { NestFactory } from '@nestjs/core';
import { SantaCloseAppModule } from './santa-close-app.module';

async function bootstrap() {
  const app = await NestFactory.create(SantaCloseAppModule);
  await app.listen(3000);
}

void bootstrap();
