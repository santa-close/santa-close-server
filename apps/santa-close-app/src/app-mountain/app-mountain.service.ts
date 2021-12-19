import { Injectable } from '@nestjs/common';

@Injectable()
export class AppMountainService {
  findAll() {
    return `This action returns all appMountain`;
  }

  findOne(id: number) {
    return `This action returns a #${id} appMountain`;
  }
}
