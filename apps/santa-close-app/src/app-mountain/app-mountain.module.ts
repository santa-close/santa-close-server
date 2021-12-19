import { Module } from '@nestjs/common';
import { AppMountainService } from './app-mountain.service';
import { AppMountainResolver } from './app-mountain.resolver';

@Module({
  providers: [AppMountainResolver, AppMountainService],
})
export class AppMountainModule {}
