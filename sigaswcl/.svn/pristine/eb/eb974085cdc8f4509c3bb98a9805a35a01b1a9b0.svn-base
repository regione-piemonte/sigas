import {NgbDateParserFormatter, NgbDateStruct} from '@ng-bootstrap/ng-bootstrap';
import {Injectable} from '@angular/core';
import * as moment from 'moment';

@Injectable()
export class NgbDateCustomParserFormatter extends NgbDateParserFormatter {
    parse(value: string): NgbDateStruct {
        if (!value) {
            return null;
        }
        const _date = moment(value, 'DD/MM/YYYY');
        return {day: _date.days(), month: _date.month(), year: _date.year()};
    }

    format(date: NgbDateStruct): string {
        if (!date) {
            return '';
        }
        const _day = date.day < 10 ? '0' + date.day : date.day;
        const _month = date.month < 10 ? '0' + date.month : date.month;
        return _day + '/' + _month + '/' + date.year;
    }
}
