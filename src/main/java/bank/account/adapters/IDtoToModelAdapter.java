package bank.account.adapters;

public interface IDtoToModelAdapter<T, S> {
    T dtoToModel(S dto);
}
