'use strict';

const React = require('react');
const ReactDOM = require('react-dom');
const client = require('./client');
var root = "/api";

class Register extends React.Component {

    constructor(props) {
        super(props);
        Register.onCreate = Register.onCreate.bind(this);
        this.state = {attributes: User.getRequiredAttributes()};
    }


    static onCreate(newUser) {
        client({
            method: 'POST',
            path: '/user',
            entity: newUser,
            headers: {'Content-Type': 'application/json'}
        });

    }


    render() {
        return (
            <div>
                <CreateDialog attributes={this.state.attributes} onCreate={Register.onCreate}/>
            </div>

        )
    }

}


class User extends React.Component {
    render() {
        return (
            <tr>
                <td>{this.props.user.username}</td>
                <td>{this.props.user.password}</td>
                <td>{this.props.user.email}</td>
            </tr>
        )
    }

    static getRequiredAttributes() {
        return ['username', 'password', 'email']
    }
}

class Users extends React.Component {

    constructor(props) {
        super(props);
        this.state = {users: []};
    }

    componentDidMount() {
        client({method: 'GET', path: '/user', registry: ""}).done(response => {
            this.setState({users: response.entity.content});
        });
    }

    render() {
        return (
            <div>
                <UserList users={this.state.users}/>
            </div>
        )
    }
}

class UserList extends React.Component {

    render() {

        const users = this.props.users.map(user =>
            <User key={user.links[1].href} user={user.user}/>);

        return (
            <div>
                <table>
                    <tbody>
                    <tr>
                        <th>Username</th>
                        <th>Password</th>
                        <th>Email</th>
                    </tr>
                    {users}
                    </tbody>
                </table>
            </div>
        )
    }
}

class UserRequest extends React.Component {
    render() {
        return (
            <tr>
                <td>{this.props.userRequest.name}</td>
                <td>{this.props.userRequest.input}</td>
                <td>{this.props.userRequest.output}</td>
            </tr>
        )
    }

}

class UserRequests extends React.Component {
    constructor(props) {
        super(props);
        this.state = {userRequests: []};
    }

    componentDidMount() {
        client({method: 'GET', path: root + '/userRequest/byUsername', registry: ""}).done(response => {
            this.setState({userRequests: response.entity});
        });
    }

    render() {

        const userRequests = this.state.userRequests.map(userRequest =>
            <UserRequest  userRequest={userRequest}/>);
        return (
            <div>
                <table>
                    <tbody>
                    <tr>
                        <th>Request Name</th>
                        <th>Input</th>
                        <th>Output</th>
                    </tr>
                    {userRequests}
                    </tbody>
                </table>
            </div>
        )

    }
}


class CreateDialog extends React.Component {

    constructor(props) {
        super(props);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.handleFacebookLogin = this.handleFacebookLogin.bind(this)
    }

    handleSubmit(e) {
        e.preventDefault();
        const newUser = {};
        this.props.attributes.forEach(attribute => {
            newUser[attribute] = ReactDOM.findDOMNode(this.refs[attribute]).value.trim();
        });
        Register.onCreate(newUser);


        // clear out the dialog's inputs
        this.props.attributes.forEach(attribute => {
            ReactDOM.findDOMNode(this.refs[attribute]).value = '';
        });

        // Navigate away from the dialog to hide it.
        window.location = "#";
    }


    handleFacebookLogin(e) {
        e.preventDefault();

        client({method: 'GET', path: '/register/facebook', registry: ""}).done(response => {
        });

    }


    render() {

        const inputs = this.props.attributes.map(attribute =>
            <p key={attribute}>
                <input type="text" placeholder={attribute} ref={attribute} className="field"/>
            </p>
        );

        return (
            <div>
                <div id="createUser" className="modalDialog">
                    <div>
                        <h2>Create new user </h2>

                        <form>
                            {inputs}
                            <br/>
                            <button onClick={this.handleSubmit}>Create</button>
                        </form>
                    </div>
                </div>
                <br/>
                <div className="container unauthenticated">
                    <form action={"https://" + window.location.host + '/login/facebook'}>
                        <input type="submit" value="Login With Facebook" />
                    </form>
                </div>
            </div>
        )
    }

}

if (document.getElementById('register')) {
    ReactDOM.render(
        <Register/>,
        document.getElementById('register')
    );
} else if (document.getElementById('users')) {
    ReactDOM.render(
        <Users/>,
        document.getElementById('users')
    );
} else if (document.getElementById('userRequests')) {
    ReactDOM.render(
        <UserRequests/>,
        document.getElementById('userRequests')
    );
}
