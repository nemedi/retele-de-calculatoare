using System.ServiceModel;

namespace Common
{
	[ServiceContract(CallbackContract = typeof(IChatCallback))]
	public interface IChat
	{
		[OperationContract(IsOneWay = true)]
		void Login(string name);

		[OperationContract(IsOneWay = true)]
		void Logout();

		[OperationContract(IsOneWay = true)]
		void Send(string to, string message);
	}
}
