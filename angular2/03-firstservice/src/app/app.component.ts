import { Component, OnInit } from '@angular/core';

import { SkillsService } from './skills.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  providers: [SkillsService]
})
export class AppComponent implements OnInit {
  title = 'List of skills:';
  skills = [];

  constructor(private skillsService: SkillsService) {

  }

  ngOnInit() {
    this.skills = this.skillsService.getSkills();
  }
}
