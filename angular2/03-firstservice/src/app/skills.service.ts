import { Injectable } from '@angular/core';

@Injectable()
export class SkillsService {

    constructor() { }

    getSkills() {
        return ["Java EE", "C#", "Angular", "NodeJS"];
    }
}