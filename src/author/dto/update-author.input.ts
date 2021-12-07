import { CreateAuthorInput } from './create-author.input';
import { PartialType } from '@nestjs/mapped-types';

export class UpdateAuthorInput extends PartialType(CreateAuthorInput) {
  id: number;
}
