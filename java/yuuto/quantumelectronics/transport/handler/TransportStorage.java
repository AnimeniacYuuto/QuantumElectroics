package yuuto.quantumelectronics.transport.handler;

import java.util.ArrayList;

import yuuto.quantumelectronics.transport.wrapper.TransportStack;

public class TransportStorage implements ITransportStorage{

	ArrayList<TransportStack> stacks;
	int maxStacks;
	
	public TransportStorage(int maxStacks){
		this.maxStacks = maxStacks;
		stacks = new ArrayList<TransportStack>();
	}
	
	@Override
	public TransportStack receiveStack(TransportStack stack, boolean simulate) {
		if(isFull())
			return null;
		if(simulate || stacks.add(stack))
			return stack;
		return null;
	}
	@Override
	public TransportStack extractStack(TransportStack stack, boolean simulate) {
		if(!stacks.contains(stack))
			return null;
		int i = stacks.indexOf(stack);
		if(simulate){
			if(stacks.get(i).stack.stackSize >= stack.stack.stackSize)
				return stack;
			return null;
		}
		if(stacks.get(i).stack.stackSize == stack.stack.stackSize){
			if(stacks.remove(stack))
				return stack;
			return null;
		}
		if(stacks.get(i).stack.stackSize > stack.stack.stackSize){
			stacks.get(i).stack.stackSize -= stack.stack.stackSize;
			return stack;
		}
		return null;
	}

	@Override
	public boolean isEmpty() {
		return stacks.size() < 1;
	}

	@Override
	public boolean isFull() {
		return stacks.size() >= maxStacks;
	}
	@Override
	public TransportStack[] getStacks() {
		return (TransportStack[])stacks.toArray();
	}

}
