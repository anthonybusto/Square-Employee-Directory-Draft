package com.abusto.square.employee_repo

import com.abusto.square.base_arch.BaseProcessor
import com.abusto.square.base_arch.ProcessorResult
import com.abusto.square.employee_repo.EmployeeAction.LoadEmployees
import com.abusto.square.employee_repo.EmployeeResult.LoadEmployeesResult
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class EmployeeProcessor(private val employeeRepo: EmployeeRepository) : BaseProcessor {

    private val loadEmployees = ObservableTransformer<LoadEmployees, LoadEmployeesResult>
    { actions ->
        actions.flatMap {
            employeeRepo.employees()
                .map(LoadEmployeesResult::Success)
                .cast(LoadEmployeesResult::class.java)
                .onErrorReturn(LoadEmployeesResult::Error)
                .startWith(LoadEmployeesResult.Loading)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }

    }



    val actionProcessor = ObservableTransformer<EmployeeAction, ProcessorResult<EmployeeResult, EmployeeEvent>> { actions ->
        actions.publish { shared ->
            shared.ofType(LoadEmployees::class.java)
                .compose(loadEmployees).cast(EmployeeResult::class.java)
                .map { ProcessorResult(it, setOf()) }
        }
    }
}





/*


class EmployeeProcessor(private val employeeRepo: EmployeeRepository) : BaseProcessor {

    private val loadEmployees = ObservableTransformer<LoadEmployees, LoadEmployeesResult>
    { actions ->
        actions.flatMap {
            employeeRepo.employees()
                .map(LoadEmployeesResult::Success)
                .cast(LoadEmployeesResult::class.java)
                .onErrorReturn(LoadEmployeesResult::Error)
                .startWith(LoadEmployeesResult.Loading)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }

    }


//    private val loadEmployees2 = ObservableTransformer<LoadEmployees, LoadEmployeesResult>
//    { actions ->
//        actions.flatMap {
//            employeeRepo.employees()
//                .map(LoadEmployeesResult::Success)
//                .cast(LoadEmployeesResult::class.java)
//                .onErrorReturn(LoadEmployeesResult::Error)
//                .startWith(LoadEmployeesResult.Loading)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//        }
//
//    }


//    val actionProcessor = loadEmployees



    val actionProcessor = ObservableTransformer<EmployeeAction, EmployeeResult> { actions ->
        actions.publish { shared ->
            shared.ofType(LoadEmployees::class.java).compose(loadEmployees)
                .cast(EmployeeResult::class.java)
        }
    }

    //TODO: If more actions are added to this processor, then treat like this:
//    val actionProcessor = ObservableTransformer<EmployeeAction, EmployeeResult> { actions ->
//        actions.publish { shared ->
//            Observable.merge(
//                shared.ofType(LoadEmployees::class.java).compose(loadEmployees),
//                shared.ofType(LoadEmployees::class.java).compose(loadEmployees2)
//            )
//        }.cast(EmployeeResult::class.java)
//    }

}



*/
