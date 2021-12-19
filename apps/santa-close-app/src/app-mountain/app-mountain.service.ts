import { Injectable } from '@nestjs/common';
import { CreateAppMountainInput } from './dto/create-app-mountain.input';
import { UpdateAppMountainInput } from './dto/update-app-mountain.input';

@Injectable()
export class AppMountainService {
  create(createAppMountainInput: CreateAppMountainInput) {
    return 'This action adds a new appMountain';
  }

  findAll() {
    return `This action returns all appMountain`;
  }

  findOne(id: number) {
    return `This action returns a #${id} appMountain`;
  }

  update(id: number, updateAppMountainInput: UpdateAppMountainInput) {
    return `This action updates a #${id} appMountain`;
  }

  remove(id: number) {
    return `This action removes a #${id} appMountain`;
  }
}
