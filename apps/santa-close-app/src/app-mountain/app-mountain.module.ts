import { Module } from '@nestjs/common';
import { AppMountainService } from './app-mountain.service';
import { AppMountainResolver } from './app-mountain.resolver';
import { MountainModule } from '@app/entity/domain/mountain/mountain.module';

@Module({
  imports: [MountainModule],
  providers: [AppMountainResolver, AppMountainService],
})
export class AppMountainModule {}
