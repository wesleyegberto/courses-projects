import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule }  from '@angular/router';
import { HttpModule }  from '@angular/http';

import { InMemoryWebApiModule } from 'angular-in-memory-web-api/in-memory-web-api.module';
import { ContactsInMemoryDS } from './contacts-in-memory-ds';

import { contactsRouting } from './contacts.routing';
import { ContactFormGuard } from './contact-form/contact-form.guard';
import { ContactsService } from './contacts.service';
import { ContactsComponent } from './contacts.component';
import { ContactStartComponent } from './contact-start.component';
import { ContactListComponent } from './contact-list/contact-list.component';
import { ContactFormComponent } from './contact-form/contact-form.component';
import { ContactDetailComponent } from './contact-detail/contact-detail.component';
import { ContactListItemComponent } from './contact-list-item/contact-list-item.component';

@NgModule({
    imports: [
      CommonModule,
      FormsModule,
      ReactiveFormsModule,
      RouterModule,
      HttpModule,
      InMemoryWebApiModule.forRoot(ContactsInMemoryDS, { delay: 600 }),
      contactsRouting
    ],
    declarations: [
      ContactsComponent,
      ContactStartComponent,
      ContactListComponent,
      ContactFormComponent,
      ContactDetailComponent,
      ContactListItemComponent
    ],
    providers: [ ContactsService, ContactFormGuard ]
})
export class ContactsModule {}
